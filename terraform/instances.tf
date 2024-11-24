# Chave SSH
# resource "aws_key_pair" "key_mick_jagger" {
#   key_name   = "key-mick-jagger"
#   public_key = file("./key-mick-jagger.pem")
# }

# Security Group
resource "aws_security_group" "sg_conexao_frontend" {
  name_prefix = "sg_conexao_frontend"
  vpc_id = aws_vpc.vpc_fastlog.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

   ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "sg_conexao_backend" {
  name_prefix = "sg_conexao_backend"
  vpc_id = aws_vpc.vpc_fastlog.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
   ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Instancias EC2
module "ec2_instance_front" {
  source = "terraform-aws-modules/ec2-instance/aws"

  name = "front-fastlog-1"

  ami                    = "ami-0866a3c8686eaeeba"
  instance_type          = "t2.micro"
  key_name               = "key-mick-jagger"
  monitoring             = true
  vpc_security_group_ids = [aws_security_group.sg_conexao_frontend.id]
  subnet_id              = aws_subnet.subnet_public_fastlog.id

  tags = {
    Name = "front-fastlog-1"
  }
}

module "ec2_instance_back" {
  source = "terraform-aws-modules/ec2-instance/aws"

  for_each = toset(["api_rastreamento-1", "banco_dados"])

  name = "back-fastlog-${each.key}"

  ami                    = "ami-0866a3c8686eaeeba"
  instance_type          = "t2.micro"
  key_name               = "key-mick-jagger"
  monitoring             = true
  vpc_security_group_ids = [aws_security_group.sg_conexao_backend.id]
  subnet_id              = aws_subnet.subnet_private_fastlog.id

  tags = {
    Name = "back-fastlog-${each.key}"
  }
}
