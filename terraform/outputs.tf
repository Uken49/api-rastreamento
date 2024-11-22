output "rds_endpoint" {
  value       = aws_db_instance.db_prod-fastlog
  description = "Endpoint do banco de dados RDS"
  sensitive = true
}