# CloudWatch Log Group para API
resource "aws_cloudwatch_log_group" "log_group_api" {
  name              = "/ecs/api_rastreamento"
  retention_in_days = 7 # Altere conforme a política de retenção desejada
  tags = {
    Name        = "log-group-api_rastreamento"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# CloudWatch Log Group para Web
resource "aws_cloudwatch_log_group" "log_group_web" {
  name              = "/ecs/web_fastlog"
  retention_in_days = 7
  tags = {
    Name        = "log-group-web_fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# CloudWatch Alarms para CPU e Memória da API
resource "aws_cloudwatch_metric_alarm" "cpu_high_api" {
  alarm_name          = "high-cpu-api_rastreamento"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 2
  metric_name         = "CPUUtilization"
  namespace           = "AWS/ECS"
  period              = 60
  statistic           = "Average"
  threshold           = 80
  alarm_description   = "Alerta quando a utilização de CPU exceder 80%."
  dimensions = {
    ClusterName = aws_ecs_cluster.fastlog_rastreamento.name
    ServiceName = aws_ecs_service.service-api_rastreamento.name
  }
  tags = {
    Name        = "cpu-high-api"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_cloudwatch_metric_alarm" "memory_high_api" {
  alarm_name          = "high-memory-api_rastreamento"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 2
  metric_name         = "MemoryUtilization"
  namespace           = "AWS/ECS"
  period              = 60
  statistic           = "Average"
  threshold           = 80
  alarm_description   = "Alerta quando a utilização de memória exceder 80%."
  dimensions = {
    ClusterName = aws_ecs_cluster.fastlog_rastreamento.name
    ServiceName = aws_ecs_service.service-api_rastreamento.name
  }
  tags = {
    Name        = "memory-high-api"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# CloudWatch Alarms para CPU e Memória da Web
resource "aws_cloudwatch_metric_alarm" "cpu_high_web" {
  alarm_name          = "high-cpu-web_fastlog"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 2
  metric_name         = "CPUUtilization"
  namespace           = "AWS/ECS"
  period              = 60
  statistic           = "Average"
  threshold           = 80
  alarm_description   = "Alerta quando a utilização de CPU exceder 80%."
  dimensions = {
    ClusterName = aws_ecs_cluster.fastlog_rastreamento.name
    ServiceName = aws_ecs_service.service-web.name
  }
  tags = {
    Name        = "cpu-high-web"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_cloudwatch_metric_alarm" "memory_high_web" {
  alarm_name          = "high-memory-web_fastlog"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 2
  metric_name         = "MemoryUtilization"
  namespace           = "AWS/ECS"
  period              = 60
  statistic           = "Average"
  threshold           = 80
  alarm_description   = "Alerta quando a utilização de memória exceder 80%."
  dimensions = {
    ClusterName = aws_ecs_cluster.fastlog_rastreamento.name
    ServiceName = aws_ecs_service.service-web.name
  }
  tags = {
    Name        = "memory-high-web"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# CloudWatch Alarms para Status de Tarefa
resource "aws_cloudwatch_metric_alarm" "running_task_count" {
  alarm_name          = "low-task-count"
  comparison_operator = "LessThanThreshold"
  evaluation_periods  = 2
  metric_name         = "RunningTaskCount"
  namespace           = "AWS/ECS"
  period              = 60
  statistic           = "Minimum"
  threshold           = 1
  alarm_description   = "Alerta quando o número de tarefas em execução for menor que 1."
  dimensions = {
    ClusterName = aws_ecs_cluster.fastlog_rastreamento.name
    ServiceName = aws_ecs_service.service-api_rastreamento.name
  }
  tags = {
    Name        = "low-task-count"
    Product     = "fastlog"
    Environment = "prod"
  }
}
