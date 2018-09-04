import threading


class PeriodicTask:
    def __init__(self, task, delay):
        self.task = task
        self.delay = delay / 1000.0
        self.timer = None

    def _run(self):
        self.task()
        self.start()

    def start(self):
        self.timer = threading.Timer(
            self.delay,
            self._run
        )
        self.timer.daemon = True
        self.timer.start()

    def stop(self):
        self.timer.cancel()
        self.timer = None

    def reset(self):
        self.stop()
        self.start()
