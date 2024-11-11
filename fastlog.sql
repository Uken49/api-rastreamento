create tabase fastlog

use fastlog

create table encomenda
    id guid,
    nome varchar(40),
    pkStatus guid,
    origem varchar (40),
    destino varchar (40);

create table status
    id guid,
    descricao_status varchar(40),
    data_criacao timestamp;
