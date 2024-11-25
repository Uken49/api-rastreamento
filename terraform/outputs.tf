output "rds_endpoint" {
  value       = aws_db_instance.db-prod_fastlog
  description = "Endpoint do banco de dados RDS"
  sensitive = true
}