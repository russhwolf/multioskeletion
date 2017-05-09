/**
 * Copyright 2013 Netflix, Inc.
 * Copyright 2014 Ashley Williams
 * Copyright 2016 MattaKis Consulting Kft.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rx.ios.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import apple.foundation.NSOperationQueue;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.schedulers.RxThreadFactory;

/**
 * Schedules actions to run on an iOS Handler thread.
 */
class HandlerThreadScheduler extends Scheduler {

    private final NSOperationQueue operationQueue;
    private static final String THREAD_PREFIX = "RxiOSScheduledExecutorPool-";

    public HandlerThreadScheduler(NSOperationQueue operationQueue) {
        this.operationQueue = operationQueue;
    }

    @Override
    public Worker createWorker() {
        return new HandlerThreadWorker(operationQueue);
    }

    private static class HandlerThreadWorker extends Worker {

        private final NSOperationQueue operationQueue;
        private final CompositeDisposable innerDisposable = new CompositeDisposable();

        HandlerThreadWorker(NSOperationQueue operationQueue) {
            this.operationQueue = operationQueue;
        }

        @Override
        public void dispose() {
            innerDisposable.dispose();
        }

        @Override
        public boolean isDisposed() {
            return innerDisposable.isDisposed();
        }

        @Override
        public Disposable schedule(final Runnable action, long delayTime, TimeUnit unit) {
            if (innerDisposable.isDisposed()) {
                return Disposables.empty();
            }

            final ScheduledAction scheduledAction = new ScheduledAction(action, operationQueue);
            final ScheduledExecutorService executor = IOSScheduledExecutorPool.getInstance();

            Future<?> future;
            if (delayTime <= 0) {
                future = executor.submit(scheduledAction);
            } else {
                future = executor.schedule(scheduledAction, delayTime, unit);
            }

            scheduledAction.add(Disposables.fromFuture(future));
            scheduledAction.addParent(innerDisposable);

            return scheduledAction;
        }

        @Override
        public Disposable schedule(final Runnable action) {
            return schedule(action, 0, null);
        }

    }

    private static final class IOSScheduledExecutorPool {
        private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(THREAD_PREFIX);
        private static final IOSScheduledExecutorPool INSTANCE = new IOSScheduledExecutorPool();
        private final ScheduledExecutorService executorService;

        private IOSScheduledExecutorPool() {
            executorService = Executors.newScheduledThreadPool(1, THREAD_FACTORY);
        }

        public static ScheduledExecutorService getInstance() {
            return INSTANCE.executorService;
        }
    }

}
