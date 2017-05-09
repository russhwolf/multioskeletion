/**
 * Copyright 2014 Netflix, Inc.
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

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import apple.foundation.NSBlockOperation;
import apple.foundation.NSOperationQueue;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * A {@code Runnable} that executes an {@code Runnable} that can be cancelled.
 */
final class ScheduledAction implements Runnable, Disposable {

    private final CompositeDisposable cancel;
    private final Runnable action;
    private final NSBlockOperation nsBlockOperation;
    private final NSOperationQueue operationQueue;
    volatile int once;
    private static final AtomicIntegerFieldUpdater<ScheduledAction> ONCE_UPDATER
            = AtomicIntegerFieldUpdater.newUpdater(ScheduledAction.class, "once");

    ScheduledAction(Runnable action, NSOperationQueue operationQueue) {
        this.action = action;
        this.operationQueue = operationQueue;
        this.cancel = new CompositeDisposable();
        nsBlockOperation = NSBlockOperation.alloc().init();
    }

    @Override
    public void run() {
        nsBlockOperation.addExecutionBlock(new NSBlockOperation.Block_addExecutionBlock() {
            @Override
            public void call_addExecutionBlock() {
                try {
                    action.run();
                } catch (Throwable e) {
                    // nothing to do but print a System error as this is fatal and there is nowhere else to throw this
                    IllegalStateException ie;
                    if (e instanceof OnErrorNotImplementedException) {
                        ie = new IllegalStateException(
                                "Exception thrown on Scheduler.Worker thread. Add `onError` handling.",
                                e);
                    } else {
                        ie = new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", e);
                    }
//                    RxJavaPlugins.getInstance().getErrorHandler().handleError(ie);
                    RxJavaPlugins.onError(ie); // TODO Not sure if this is correct replacement for original line commented above
                    Thread thread = Thread.currentThread();
                    thread.getUncaughtExceptionHandler().uncaughtException(thread, ie);
                } finally {
                    dispose();
                }
            }
        });
        operationQueue.addOperation(nsBlockOperation);
    }

    @Override
    public void dispose() {
        if (ONCE_UPDATER.compareAndSet(this, 0, 1)) {
            nsBlockOperation.cancel();
            cancel.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        return cancel.isDisposed();
    }

    /**
     * Adds a {@code Disposable} to the {@link CompositeDisposable} to be later cancelled on
     * dispose
     *
     * @param s disposable to add
     */
    void add(Disposable s) {
        cancel.add(s);
    }

    /**
     * Adds a parent {@link CompositeDisposable} to this {@code ScheduledAction}
     * so when the action is cancelled or terminates, it can remove itself from this parent
     *
     * @param parent the parent {@code CompositeDisposable} to add
     */
    void addParent(CompositeDisposable parent) {
        cancel.add(new Remover(this, parent));
    }

    /**
     * Remove a child disposable from a composite when disposing.
     */
    private static final class Remover implements Disposable {

        final Disposable s;
        final CompositeDisposable parent;
        volatile int once;
        static final AtomicIntegerFieldUpdater<Remover> ONCE_UPDATER
                = AtomicIntegerFieldUpdater.newUpdater(Remover.class, "once");

        Remover(Disposable s, CompositeDisposable parent) {
            this.s = s;
            this.parent = parent;
        }

        @Override
        public boolean isDisposed() {
            return s.isDisposed();
        }

        @Override
        public void dispose() {
            if (ONCE_UPDATER.compareAndSet(this, 0, 1)) {
                parent.remove(s);
            }
        }

    }

}
