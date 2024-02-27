# SpringBatchRestartExample
A simple project to check out how a SB job restart from an intermediate execution state.

Starting from a configuration with a MySQL database, a Maven repository and a Java VM 17 (may be OK for JVM 11?).

- Execute App.java and stop the execution between element 11 and 19.
- Check the last job excution id in batch_job_execution table.
- Set 'jobExecutionId' in main() method from RestartApp.java with the retrieved value.
- Execute RestartApp.java.
- You will see that the previous interrupted execution goes on the state you stopped it.
