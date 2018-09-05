from threading import Thread
from threading import Event as ThreadEvent
from time import time


class PeriodicTask:
    # threading.Timer is very inaccurate, using custom thread.
    def __init__(self, task, delay):
        self.task = task
        self.delay = delay / 1000.0
        self.timer = None
        self.stop_event = None

    def _run(self, task, delay, stop_event):
        stop_event.wait(delay)
        next_call = time()
        while not stop_event.is_set():
            next_call = next_call + delay
            task()
            stop_event.wait(next_call - time())

    def start(self):
        self.stop_event = ThreadEvent()
        self.timer = Thread(
            target=self._run,
            args=(self.task, self.delay, self.stop_event)
        )
        self.timer.daemon = True
        self.timer.start()

    def stop(self):
        self.stop_event.set()

    def reset(self):
        self.stop()
        self.start()
