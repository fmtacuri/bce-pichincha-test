CREATE TABLE "bcp_cliente" (
"id_cliente" varchar(20) NOT NULL,
"nombres" varchar(70) NOT NULL,
"identificacion" int8 NOT NULL,
"direccion" varchar(25) NOT NULL,
"apellidos" varchar(70) NOT NULL,
PRIMARY KEY ("id_cliente")
);
CREATE TABLE "bcp_producto" (
"cod_producto" varchar(10) NOT NULL,
"descripcion" varchar(45) NOT NULL,
"stock" int4 NOT NULL DEFAULT 0,
"precio" float4 NOT NULL,
PRIMARY KEY ("cod_producto")
);
CREATE TABLE "bcp_head_pedido" (
"cod_fac" serial8 NOT NULL,
"id_cliente" varchar(20) NOT NULL,
"fecha" date NOT NULL,
PRIMARY KEY ("cod_fac")
);
CREATE TABLE "bcp_det_pedido" (
"cod_factura" int8 NOT NULL,
"cod_inventario" varchar(25) NOT NULL,
"cantidad" int4 NOT NULL,
"precio_venta" float4 NOT NULL,
"no_fila" int4 NOT NULL,
PRIMARY KEY ("no_fila", "cod_factura")
);
CREATE TABLE "bcp_proveedor" (
"cod_proveedor" serial8 NOT NULL,
"nombre" varchar(70) NOT NULL,
"direccion" varchar(25) NOT NULL,
"telefono" varchar(20) NOT NULL,
"nombre_empresa" varchar(70) NOT NULL,
PRIMARY KEY ("cod_proveedor")
);
CREATE TABLE "bcp_inventario" (
"cod_producto" varchar(10) NOT NULL,
"cod_proveedor" int8 NOT NULL,
"stock" int4 NOT NULL,
"precio" float4 NOT NULL,
"id_inventario" varchar(25) NOT NULL,
PRIMARY KEY ("id_inventario")
);

ALTER TABLE "bcp_head_pedido" ADD CONSTRAINT "fk_id_cliente" FOREIGN KEY ("id_cliente") REFERENCES "bcp_cliente" ("id_cliente");
ALTER TABLE "bcp_det_pedido" ADD CONSTRAINT "fk_cod_factura" FOREIGN KEY ("cod_factura") REFERENCES "bcp_head_pedido" ("cod_fac");
ALTER TABLE "bcp_inventario" ADD CONSTRAINT "fk_cod_proveedor" FOREIGN KEY ("cod_proveedor") REFERENCES "bcp_proveedor" ("cod_proveedor");
ALTER TABLE "bcp_inventario" ADD CONSTRAINT "fk_cod_producto" FOREIGN KEY ("cod_producto") REFERENCES "bcp_producto" ("cod_producto");
ALTER TABLE "bcp_det_pedido" ADD CONSTRAINT "fk_cod_inventario" FOREIGN KEY ("cod_inventario") REFERENCES "bcp_inventario" ("id_inventario");

