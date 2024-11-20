variable "ACCESS_KEY_ID" {
  type     = string
  nullable = false
  sensitive = true
}

variable "SECRET_ACCESS_KEY" {
  type     = string
  nullable = false
  sensitive = true
}

variable "SESSION_TOKEN" {
  type     = string
  nullable = false
  sensitive = true
}

variable "REGION" {
  type = string
  default = "us-east-1"
  nullable = false
  sensitive = false
}