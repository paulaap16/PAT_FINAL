create table Articulo(id integer not null, pedido integer, foto integer, size varchar(255), precioSize integer, cantidad integer, primary key(id));
create table Foto(id integer not null, precio integer, url varchar(255), primary key(id));
create table Pedido(id integer not null, usuario integer, fecha varchar(255), precioTotal integer, direccion varchar(255), primary key(id));
create table Token(id integer not null, usuario integer, primary key(id));
create table Usuario(id integer not null, name varchar(255), email varchar(255), password varchar(255), role varchar(255), primary key(id));