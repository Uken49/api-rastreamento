# VPC
resource "aws_vpc" "vpc_fastlog" {
  cidr_block = "10.0.0.0/27"
}

# Internet Gateway
resource "aws_internet_gateway" "igw_fastlog" {
  vpc_id = aws_vpc.vpc_fastlog.id
}

resource "aws_eip" "eip_fastlog" {
  associate_with_private_ip = false
}

# Nat Gateway
resource "aws_nat_gateway" "nat_fastlog" {
  allocation_id     = aws_eip.eip_fastlog.id
  subnet_id         = aws_subnet.subnet_public_fastlog.id
  connectivity_type = "public"
  depends_on = [aws_internet_gateway.igw_fastlog]
}

# Subnets
resource "aws_subnet" "subnet_public_fastlog" {
  vpc_id                  = aws_vpc.vpc_fastlog.id
  cidr_block              = "10.0.0.0/28"
  availability_zone       = "us-east-1a"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "subnet_private_fastlog" {
  vpc_id            = aws_vpc.vpc_fastlog.id
  cidr_block        = "10.0.0.16/28"
  availability_zone = "us-east-1a"
}

# Route Tables
resource "aws_route_table" "rt_public_fastlog" {
  vpc_id = aws_vpc.vpc_fastlog.id

  route {
    cidr_block = "10.0.0.0/28"
    gateway_id = aws_internet_gateway.igw_fastlog.id
  }

}

resource "aws_route_table" "rt_private_fastlog" {
  vpc_id = aws_vpc.vpc_fastlog.id

  route {
    cidr_block = "10.0.0.16/28"
  }

}

resource "aws_route_table_association" "rta_public_fastlog" {
  subnet_id      = aws_subnet.subnet_public_fastlog.id
  route_table_id = aws_route_table.rt_public_fastlog.id
}

resource "aws_route_table_association" "rta_private_fastlog" {
  subnet_id      = aws_subnet.subnet_private_fastlog.id
  route_table_id = aws_route_table.rt_private_fastlog.id
}
