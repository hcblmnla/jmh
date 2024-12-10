## Benchmark Summary

As a result, it is quite expected results according to the articles
(yes, using the constructed lambda is faster than direct access)

| Benchmark                  | Mode | Cnt | Score   | Error | Units |
|----------------------------|------|-----|---------|-------|-------|
| BenchmarkTest.directAccess | avgt |     | 197,101 |       | ns/op |
| BenchmarkTest.reflection   | avgt |     | 199,074 |       | ns/op |
| BenchmarkTest.methodHandle | avgt |     | 212,467 |       | ns/op |
| BenchmarkTest.lambdaMeta   | avgt |     | 191,701 |       | ns/op |
