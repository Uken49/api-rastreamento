resource "aws_db_subnet_group" "db-subnet_group" {
  name = "db-subnet_group"
  subnet_ids = [
    aws_subnet.private-fastlog-east_1a.id,
    aws_subnet.private-fastlog-east_1b.id
  ]

  tags = {
    Name        = "db-subnet_group"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_db_instance" "db_prod-fastlog" {
  identifier              = "prod-fastlog"
  allocated_storage       = 20
  engine                  = "postgres"
  engine_version          = "16.3"
  instance_class          = "db.t3.micro"
  username                = "postgres"
  password                = var.RDS_PASSWORD
  db_name                 = "fastlog"
  publicly_accessible     = false
  vpc_security_group_ids  = [aws_security_group.conexao_backend.id]
  db_subnet_group_name    = aws_db_subnet_group.db-subnet_group.name
  skip_final_snapshot     = true
  backup_retention_period = 7
  multi_az                = false

  tags = {
    Name        = "db_prod-fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}
