# Clusters
resource "aws_ecs_cluster" "fastlog_rastreamento" {
  name = "web_application"

  setting {
    name  = "containerInsights"
    value = "enabled"
  }

  tags = {
    Name        = "ecs-fastlog_rastreamento"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Task Definitions
resource "aws_ecs_task_definition" "task-api_rastreamento" {
  depends_on               = [aws_db_instance.db-prod_fastlog]
  family                   = "api_rastreamento"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"

  container_definitions = jsonencode([
    {
      name      = "api_rastreamento",
      image     = "uken49/api-rastreamento:latest",
      cpu       = 0,
      memory    = 1024,
      essential = true,
      portMappings = [
        {
          containerPort = 8000
          hostPort      = 8000
          protocol      = "tcp"
          appProtocol   = "http"
        }
      ],
      environment = [
        {
          name  = "DATASOURCE_POSTGRES_PASSWORD"
          value = var.RDS_PASSWORD
        },
        {
          name  = "DATASOURCE_POSTGRES_URL"
          value = aws_db_instance.db-prod_fastlog.endpoint
        }
      ]
    }
  ])

  tags = {
    Name        = "task-api_rastreamento"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_ecs_task_definition" "task-web_app" {
  depends_on               = [aws_ecs_task_definition.task-api_rastreamento]
  family                   = "web_fastlog"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"

  container_definitions = jsonencode([
    {
      name      = "web",
      image     = "uken49/web-fastlog:latest",
      cpu       = 0,
      memory    = 1024,
      essential = true,
      portMappings = [
        {
          containerPort = 80
          protocol      = "tcp"
        }
      ]
    }
  ])

  tags = {
    Name        = "task-web_app"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Services
resource "aws_ecs_service" "service-api_rastreamento" {
  name            = "api_rastreamento"
  cluster         = aws_ecs_cluster.fastlog_rastreamento.id
  task_definition = aws_ecs_task_definition.task-api_rastreamento.arn
  desired_count   = 2
  launch_type     = "FARGATE"

  network_configuration {
    subnets = [
      aws_subnet.private-fastlog-east_1a.id,
      aws_subnet.private-fastlog-east_1b.id
    ]
    security_groups  = [aws_security_group.conexao_backend.id]
    assign_public_ip = false
  }

  tags = {
    Name        = "service-api_rastreamento"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_ecs_service" "service-web" {
  name            = "web"
  cluster         = aws_ecs_cluster.fastlog_rastreamento.id
  task_definition = aws_ecs_task_definition.task-web_app.arn
  desired_count   = 2
  launch_type     = "FARGATE"

  network_configuration {
    subnets = [
      aws_subnet.public-fastlog-east_1a.id,
      aws_subnet.public-fastlog-east_1a.id
    ]
    security_groups  = [aws_security_group.conexao_frontend.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.tg-web.arn
    container_name   = "web"
    container_port   = 80
  }

  tags = {
    Name        = "service-web"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Load Balancer
resource "aws_lb" "lb-web" {
  name               = "lb-web"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.conexao_frontend.id]
  subnets            = [aws_subnet.public-fastlog-east_1a.id, aws_subnet.public-fastlog-east_1b.id]

  tags = {
    Name        = "lb-web"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Target Group para Web
resource "aws_lb_target_group" "tg-web" {
  name        = "tg-web"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = aws_vpc.vpc-fastlog.id
  target_type = "ip"

  tags = {
    Name        = "tg-vpc_fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Listener para LB
resource "aws_lb_listener" "lb-listener_web" {
  load_balancer_arn = aws_lb.lb-web.arn
  port              = 80
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.tg-web.arn
  }
}
