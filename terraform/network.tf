# Criar a VPC com o CIDR principal
resource "aws_vpc" "vpc-fastlog" {
  cidr_block = "10.0.0.0/26"

  tags = {
    Name        = "vpc-fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Associação para adicionar um CIDR secundário
resource "aws_vpc_ipv4_cidr_block_association" "vpc-fastlog_additional_cidr" {
  vpc_id     = aws_vpc.vpc-fastlog.id
  cidr_block = "10.1.0.0/26"
}

# Internet Gateway
resource "aws_internet_gateway" "igw-fastlog" {
  vpc_id = aws_vpc.vpc-fastlog.id

  tags = {
    Name        = "igw-fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_eip" "eip-fastlog" {
  for_each = {
    "east-1a" = aws_subnet.public-fastlog-east_1a.id
    "east-1b" = aws_subnet.public-fastlog-east_1b.id
  }

  tags = {
    Name        = "eip-${each.key}"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Subnets
# Public Subnets
resource "aws_subnet" "public-fastlog-east_1a" {
  vpc_id                  = aws_vpc.vpc-fastlog.id
  cidr_block              = "10.0.0.0/28"
  availability_zone       = "us-east-1a"
  map_public_ip_on_launch = true

  tags = {
    Name        = "subnet_public-east_1a"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_subnet" "public-fastlog-east_1b" {
  vpc_id                  = aws_vpc.vpc-fastlog.id
  cidr_block              = "10.1.0.0/28"
  availability_zone       = "us-east-1b"
  map_public_ip_on_launch = true

  tags = {
    Name        = "subnet_public-east_1b"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Private Subnets
resource "aws_subnet" "private-fastlog-east_1a" {
  vpc_id            = aws_vpc.vpc-fastlog.id
  cidr_block        = "10.0.0.16/28"
  availability_zone = "us-east-1a"

  tags = {
    Name        = "subnet_private-east_1a"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_subnet" "private-fastlog-east_1b" {
  vpc_id            = aws_vpc.vpc-fastlog.id
  cidr_block        = "10.1.0.16/28"
  availability_zone = "us-east-1a"

  tags = {
    Name        = "subnet_private-east_1b"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Nat Gateway
resource "aws_nat_gateway" "nat-fastlog" {
  for_each = {
    "east-1a" = aws_subnet.public-fastlog-east_1a.id
    "east-1b" = aws_subnet.public-fastlog-east_1b.id
  }

  allocation_id     = aws_eip.eip-fastlog[each.key].id
  subnet_id         = each.value
  connectivity_type = "public"

  depends_on        = [aws_internet_gateway.igw-fastlog]

  tags = {
    Name        = "nat-private-${each.key}"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Route Tables
resource "aws_route_table" "rt_public-fastlog" {
  vpc_id = aws_vpc.vpc-fastlog.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-fastlog.id
  }

  tags = {
    Name        = "rt_public-fastlog"
    Product     = "fastlog"
    Environment = "prod"
  }
}

resource "aws_route_table" "rt_private-fastlog" {
  for_each = {
    "east-1a" = aws_nat_gateway.nat-fastlog["east-1a"].id
    "east-1b" = aws_nat_gateway.nat-fastlog["east-1b"].id
  }

  vpc_id = aws_vpc.vpc-fastlog.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = each.value
  }

  tags = {
    Name        = "rt_private-fastlog-${each.key}"
    Product     = "fastlog"
    Environment = "prod"
  }
}

# Associação para sub-redes públicas
resource "aws_route_table_association" "rta_public-fastlog-east-1a" {
  subnet_id      =  aws_subnet.public-fastlog-east_1a.id
  route_table_id = aws_route_table.rt_public-fastlog.id
}

resource "aws_route_table_association" "rta_public-fastlog-east-1b" {
  subnet_id      =  aws_subnet.public-fastlog-east_1b.id
  route_table_id = aws_route_table.rt_public-fastlog.id
}

# Associação para sub-redes privadas
resource "aws_route_table_association" "rta_private_fastlog" {
  for_each = {
    "east-1a" = aws_subnet.private-fastlog-east_1a.id
    "east-1b" = aws_subnet.private-fastlog-east_1b.id
  }

  subnet_id      = each.value
  route_table_id = aws_route_table.rt_private-fastlog[each.key].id
}