import threading
import datetime
import time


class PeriodicTask:
    counter = 0

    def __init__(self, task, delay):
        self.task = task
        self.delay = delay / 1000.0
        self.timer = None
        self.stop_event = None
        self.stopping = False
        self.id = -1

    def _run(self, stop_event):
        next_call = time.time()
        while not stop_event.is_set():
            if not self.stopping:
                self.task()
            next_call = next_call + self.delay
            stop_event.wait(next_call - time.time())

    def start(self):
        if self.timer is None:
            self.stopping = False
            self.id = PeriodicTask.counter
            PeriodicTask.counter += 1
            self.stop_event = threading.Event()
            self.stop_event.wait(self.delay)
            # threading.Timer is very inaccurate, using custom thread.
            self.timer = threading.Thread(target=self._run, args=(self.stop_event,))
            self.timer.daemon = True
            self.timer.start()

    def stop(self):
        self.stopping = True
        self.stop_event.set()
        self.timer.join()
        self.stop_event = None
        self.timer = None

    def reset(self):
        self.stop()
        self.start()


def timed_print(*args):
    print(datetime.datetime.now(), '-', threading.currentThread().getName(), *args)


if __name__ == '__main__':
    clock = PeriodicTask(lambda x: timed_print('Thread no.', x), 1000)
    timed_print('starting')
    clock.start()
    time.sleep(5.5)
    timed_print('wakeup')
    timed_print('reseting')
    clock.reset()
    time.sleep(5.5)
    timed_print('wakeup')
    timed_print('stopping')
    clock.stop()
    time.sleep(5.5)
    clock = PeriodicTask(lambda x: timed_print('New thread no.', x), 1000)
    clock.start()
    time.sleep(10)
