package cool.graph.metrics

import cool.graph.profiling.JvmProfiler

object ClientSharedMetrics extends MetricsManager {

  // CamelCase the service name read from env
  override def serviceName =
    sys.env
      .getOrElse("SERVICE_NAME", "ClientShared")
      .split("-")
      .map { x =>
        x.head.toUpper + x.tail
      }
      .mkString

  JvmProfiler.schedule(this)

  val schemaBuilderBuildTimerMetric = defineTimer("schemaBuilderBuildTimer", CustomTag("projectId", recordingThreshold = 600))
  val projectCacheGetCount          = defineCounter("projectCacheGetCount")
  val projectCacheMissCount         = defineCounter("projectCacheMissCount")
  val sqlDataChangeMutactionTimer   = defineTimer("sqlDataChangeMutactionTimer", CustomTag("projectId", recordingThreshold = 1000))
  val queryPermissionCounter        = defineFlushingCounter("queryPermissionsCounter", CustomTag("projectId", recordingThreshold = 50))
  val permissionCheckingTimer       = defineTimer("permissionCheckingTimer", CustomTag("projectId", recordingThreshold = 500))
}
