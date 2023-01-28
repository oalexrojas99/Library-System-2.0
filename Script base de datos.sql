DROP DATABASE IF EXISTS librarysystem_v2;
CREATE DATABASE librarysystem_v2;
USE librarysystem_v2;

/*SEGURIDAD*/
CREATE TABLE usuario (
  idusuario INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombreusuario VARCHAR(256) NOT NULL UNIQUE,
  contrasena VARCHAR(256) NOT NULL,
  estaactivo TINYINT NOT NULL DEFAULT '1'
);

/*NEGOCIO*/
CREATE TABLE tema (
  idtema INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  descripcion VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE estudiante (
  idestudiante INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(64) NOT NULL,
  apellidopaterno VARCHAR(32) NOT NULL,
  apellidomaterno VARCHAR(32) NOT NULL,
  estadoestudiante TINYINT NOT NULL DEFAULT '1'
);

CREATE TABLE idioma(
	ididioma INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE libro (
  idlibro INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(32) NOT NULL UNIQUE,
  titulo VARCHAR(256) NOT NULL UNIQUE,
  autor VARCHAR(512) NOT NULL,
  idtema INT UNSIGNED NOT NULL,
  ididioma INT UNSIGNED NOT NULL,
  anio SMALLINT NOT NULL
);
ALTER TABLE libro
	ADD CONSTRAINT fk_libro_tema FOREIGN KEY (idtema) REFERENCES tema (idtema) ON DELETE CASCADE,
    ADD CONSTRAINT fk_libro_idioma FOREIGN KEY (ididioma) REFERENCES idioma (ididioma) ON DELETE CASCADE;

CREATE TABLE ejemplar(
	idejemplar INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idlibro INT UNSIGNED NOT NULL,
    estadoejemplar TINYINT NOT NULL DEFAULT '1' -- 0: No disponible, 1: Disponible
);
ALTER TABLE ejemplar
	ADD CONSTRAINT fk_libro_ejemplar FOREIGN KEY (idlibro) REFERENCES libro (idlibro) ON DELETE CASCADE;
    
CREATE TABLE prestamo (
  idprestamo INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  idestudiante INT UNSIGNED NOT NULL,
  idejemplar INT UNSIGNED NOT NULL,
  fechaprestamo DATE NOT NULL,
  numdiasprestamo tinyint NOT NULL,
  estadoprestamo tinyint NOT NULL DEFAULT '1' -- 0: Finalizado, 1: En vigencia, 2: Fuera de plazo
);
ALTER TABLE prestamo
	ADD CONSTRAINT fk_prestamo_estudiante FOREIGN KEY (idestudiante) REFERENCES estudiante (idestudiante) ON DELETE CASCADE,
	ADD	CONSTRAINT fk_prestamo_ejemplar FOREIGN KEY (idejemplar) REFERENCES ejemplar (idejemplar) ON DELETE CASCADE;

/*Triger 1 --> Crear el primer ejemplar cuando se registra un nuevo libro*/
DELIMITER ||
CREATE TRIGGER TR_generar_primer_ejemplar
AFTER INSERT ON libro
FOR EACH ROW
BEGIN
	INSERT INTO ejemplar (idlibro) VALUES (NEW.idlibro);
END;
||

/*Triger 2 --> Modificar estado de ejemplar a NO DISPONIBLE cuando se genera un préstamo con el ejemplar en cuestión.*/
DELIMITER ||
CREATE TRIGGER TR_modificar_estado_libro_1
AFTER INSERT ON prestamo
FOR EACH ROW
BEGIN
	UPDATE ejemplar SET estadoejemplar = 0 WHERE idejemplar = NEW.idejemplar;
END;
||

/*Triger 2 --> Modificar estado de libro a DISPONIBLE cuando un préstamo pasa de estado VIGENTE o FUERA DE PLAZO a FINALIZADO*/
DELIMITER ||
CREATE TRIGGER TR_modificar_estado_libro_2
AFTER UPDATE ON prestamo
FOR EACH ROW
BEGIN
	IF NEW.estadoprestamo = 0
		THEN UPDATE ejemplar SET estadoejemplar = 1 WHERE idejemplar = NEW.idejemplar;
	END IF;
END;
||

/*Vistas*/
DROP VIEW IF EXISTS v_libros_temas_idiomas;
CREATE VIEW v_libros_temas_idiomas
AS
	SELECT
    l.*, 
    t.descripcion AS 'tema',
    i.descripcion AS 'idioma'
    FROM libro l
    INNER JOIN tema t ON l.idtema = t.idtema
    INNER JOIN idioma i ON l.ididioma = i.ididioma;

SELECT * FROM v_libros_temas_idiomas WHERE tema = 'SQL';

DROP VIEW IF EXISTS v_detalle_ejemplares;
CREATE VIEW v_detalle_ejemplares
AS
	SELECT
		e.*,
		v.isbn,
		v.titulo,
		v.autor,
		v.idtema,
		v.ididioma,
		v.anio,
		v.tema,
		v.idioma,
    FROM ejemplar e
    INNER JOIN v_libros_temas_idiomas v ON e.idlibro = v.idlibro;
select * from v_detalle_ejemplares

DROP VIEW IF EXISTS v_detalle_prestamos;
CREATE VIEW v_detalle_prestamos
AS
	SELECT
		ej.*,
        es.*,
        p.idprestamo,
        p.fechaprestamo,
        p.numdiasprestamo,
        p.estadoprestamo,
        DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) as fechadevolucion,
        (CASE
			WHEN DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) >= CURDATE() AND p.estadoprestamo = 1 THEN 'En vigencia'
            WHEN DATE_ADD(p.fechaprestamo, INTERVAL p.numdiasprestamo DAY) < CURDATE() AND p.estadoprestamo = 1 THEN 'Fuera de plazo'
            ELSE 'Finalizado'
        END) AS descrpicion_prestamo-- 0: Finalizado, 1: En vigencia, 2: Fuera de plazo
    FROM prestamo p
    INNER JOIN v_detalle_ejemplares ej ON ej.idejemplar = p.idejemplar
    INNER JOIN estudiante es ON p.idestudiante = es.idestudiante;

SELECT * FROM v_detalle_prestamos;
SELECT * FROM v_detalle_prestamos WHERE fechadevolucion < CURDATE() AND estadoprestamo = 1
 
DROP VIEW IF EXISTS v_libros_ejemplares;
CREATE VIEW v_libros_ejemplares
AS
	SELECT
    l.*
    FROM libro l
    INNER JOIN ejemplar e ON l.idlibro = e.idlibro
    WHERE
		e.estadoejemplar = 1
    GROUP BY l.idlibro
    HAVING COUNT(*) > 1
/*Procedimientos almacenados*/
DROP PROCEDURE IF EXISTS SP_consulta_catalogo_libros;

DELIMITER ||
CREATE PROCEDURE SP_consulta_catalogo_libros (IN anio_1 SMALLINT, IN anio_2 SMALLINT, IN tema_id INT)
BEGIN
	SELECT *
    FROM v_libros_temas_idiomas
    WHERE
    anio BETWEEN anio_1 AND anio_2
    AND idtema = tema_id;
END;
||

CALL SP_consulta_catalogo_libros (2000, 2020, 4);

/*Inserción de data*/
-- Usuario
INSERT INTO usuario (nombreusuario, contrasena) VALUES 
	('oalex.rs','123'),
    ('al.ps','123'),
    ('ez005','123');

-- Tema
INSERT INTO tema (descripcion) VALUES 
	('Ciencias en la Computación'),
    ('Algorítmica'),
    ('Inteligencia Artificial'),
    ('Base de datos'),
    ('Análisis y Diseño de Sistemas'),
    ('Frameworks y API\'s'),
    ('Lenguaje Ensamblador'),
    ('Entornos de Desarrollo'),
    ('Sistemas Digitales'),
    ('Desarrollo de Aplicaciones Web'),
    ('Desarrollo Frontend'),
    ('Desarrollo Backend'),
    ('Estructura de datos'),
    ('Normativas de diseño web'),
    ('Quality Assurance'),
    ('SQL'),
    ('Servicios en AWS'),
    ('Modelos relacionales y no relacioneales'),
    ('Metodologías de software'),
    ('NoSQL'),
    ('UX');
    
-- Estudiante
INSERT INTO estudiante (nombres, apellidopaterno, apellidomaterno, estadoestudiante) VALUES 
	('Oscar Alexander','Rojas','Soplin', DEFAULT),
    ('Alejandro','Perez','Sotomayor', DEFAULT),
    ('Mariana','Urbina','Maldonado', DEFAULT),
    ('Kiara','Jimenez','Asurza', DEFAULT),
    ('Hector','Gutierrez','Caceres', 0),
    ('Liliana','Barboza','Alvarez', 0),
    ('Oscar Miguel','Salazar','Herrera', DEFAULT),
    ('Hector Rodrigo','Huaripata','Huaccha', DEFAULT),
    ('Sebastian','Ortiz','Urbai', DEFAULT),
    ('Yeison Samir','Cano','Carbajo', DEFAULT),
    ('Angela','Paredes','Curi', DEFAULT),
    ('Marcelo Juan','Espinoza','Sevilla', DEFAULT),
    ('Sofia Maria','Romero','Vasquez', DEFAULT),
    ('Henry Aaron','Ruiz','Baldeon', DEFAULT),
    ('Mary Katie','Mendoza','Fernandez', DEFAULT),
    ('Samael','Meneses','Osorio', DEFAULT),
    ('Erick Juan','Pimentel','Chavez', DEFAULT),
    ('Carlos Marcos','Apaza','Bravo', DEFAULT),
    ('Luz Yajaira','Flores','Baltazar', DEFAULT),
    ('Fiorella','De la Cruz','Calle', DEFAULT),
    ('Renato Oscar','Cardenas','Cristobal', DEFAULT),
    ('Kevin','Arancibia','Castillo', DEFAULT),
    ('Fatima Elena','Garcia','Ulloa', DEFAULT),
    ('Hugo Josue','Paz','Diaz', DEFAULT),
    ('Giancarlos Mateo','Cubas','Astocondor', DEFAULT),
    ('Ruth Violeta','Poma','Villalobos', DEFAULT),
    ('Julisa Milagros','Hurtado','Mora', DEFAULT),
    ('Percy Victor','Ore','Alvarez', DEFAULT),
    ('Angel Antonio','Flores','Leon', DEFAULT),
    ('Jack Alexis','Huapaya','Palomino', DEFAULT),
    ('Stephano Emmanuel','Porras','Escobar', 0),
    ('Gabriel Andre','Angulo','Escobar', 0),
    ('William Jack','Peralta','Guerero', DEFAULT),
    ('Michael Ernersto','Segovia','Murakami', DEFAULT),
    ('Christian','Ramon','Silvestre', DEFAULT),
    ('Roy Andy','Andia','Huertas', DEFAULT),
    ('Coral Angie','Quiroz','Cruzado', DEFAULT),
    ('Alan Daniel','Cotrina','Caro', DEFAULT),
    ('Dennis Pedro','Cutipa','Zacarias', DEFAULT),
    ('Jaime Manuel','Cueva','Cardenas', DEFAULT),
    ('Enrique Damian','Borda','Diaz', DEFAULT),
    ('Farid','Dominguez','Morales', DEFAULT),
    ('Cecilia Ana','Alarcon','Astudillo', DEFAULT),
    ('Alexander Ian','Burgos','Figueroa', DEFAULT),
    ('Gian Luis','Galarza','Iquira', DEFAULT),
    ('Robert Kyle','Alcarraz','Gonzalez', DEFAULT),
    ('Gabriel Andre','Lavado','Jaramillo', DEFAULT),
    ('Gabriel Andre','Guerrero','Corne', DEFAULT),
    ('Max Alonso','Guevara','Hernandez', DEFAULT),
    ('Cesar','Ayala','Caceres', DEFAULT),
    ('Flor Lizzeth','Herrera','Rojas', DEFAULT),
    ('Fabricio Joel','Lare','Valera', DEFAULT),
    ('Anthony Radamel','Alva','Carrillo', DEFAULT),
    ('Rudy Hector','La Matta','Tapia', DEFAULT),
    ('Frank','Lezma','Abad', DEFAULT),
    ('Ana Maria','Lopez','Livias', DEFAULT),
    ('Hiroshi Claudio','Obregon','Cortez', DEFAULT),
    ('Fernanda','Godoy','Apaza', DEFAULT),
    ('Wilmer Daniel','Campos','Meza', DEFAULT),
    ('Arnold Eduardo','Jara','Escobar', DEFAULT),
    ('Omar Gustavo','Mercado','Molina', DEFAULT),
    ('Alejandra Saka','Huaman','Suca', DEFAULT),
    ('Jhon Rafael','Arenas','Mora', DEFAULT),
    ('Miguel Leonel','Nolasco','Galindo', DEFAULT),
    ('Paul Feliciano','Palomino','Machuca', DEFAULT),
    ('Xiomara Vanesa','Prado','Ramos', DEFAULT),
    ('Angie Carolina','Alatrista','Pastor', DEFAULT),
    ('Isabel','Huanca','Ramirez', DEFAULT),
    ('Gabriela Daniela','Ponce','Jara', DEFAULT),
    ('Irma Celeste','Hinostroza','Pavis', DEFAULT),
    ('Paulina Ariana','Avila','Quevedo', DEFAULT),
    ('Teresa Maria','Fierro','Quispe', DEFAULT),
    ('Tatiana Feliciana','Linares','Galdos', DEFAULT),
    ('Naomi Angelica','Paniagua','Cespedes', DEFAULT),
    ('Patricia Wendy','Roca','Rodas', DEFAULT),
    ('Alicia Alexa','Sifuentes','Reyes', DEFAULT),
    ('Angela Susana','Arroyo','Samaniego', DEFAULT),
    ('Jefferson Anderson','Salinas','Salvatierra', DEFAULT),
    ('Nicole Melanie','Villalta','Moreno', DEFAULT),
    ('Carmen','Susanibar','Reales', DEFAULT),
    ('Kenneth Gerson','Angulo','Escobar', DEFAULT),
    ('Diana Laura','Terrones','Cruz', 0),
    ('Evelyn Roxana','Bazan','Sierra', 0),
    ('Bianca Betsy','Alvarado','Camones', 0);

-- Idioma
INSERT INTO idioma (descripcion) VALUES 
	('Español'),
    ('Inglés'),
    ('Portugués');
    
INSERT INTO libro (isbn, titulo, autor, idtema, ididioma, anio) VALUES
	('9541203587412','The Road to Learn React: Your Journey to Master Plain Yet','Robin Wieruch',11, 2, 2017),
    ('9788478290598','Patrones de Diseño','Erich Gamma',2, 1, 2002),
    ('9788499641249','Desarrollo de Bases de Datos: Casos Prácticos Desde el Análisis a la Implementación','Dolores Cuadra Fernández', 4, 1, 2013),
    ('9788448179267','EBOOK Fundamentos de bases de datos','Abraham Silberschatz, Henry Korth, S. Sudarshan', 4, 1, 2015),
    ('9781596931862','Handbook of Software Quality Assurance','G. Gordon Schulmeyer', 15, 2, 2007),
    ('9781590531863','Customer Oriented Software Quality Assurance','Frank P. Ginac', 15, 2, 1988),
    ('9781329224278','Scrum and XP from the Trenches','Henrik Kniberg', 19, 2, 2007),
    ('9781484221891','Material Design Implementation with AngularJS: UI Component Framework', 'V. Keerti Kotaru', 19, 2, 2016),
    ('9785210236598','Fundamentos de bases de datos','Abraham Silberschatz',4, 1, 1997),
    ('9751240315427','Microsoft SQL Server 2019 Standard incl. 1 Device CAL','Microsoft Co.', 16, 2, 2019),
    ('9510320528447','Oracle 12c PL/SQL: Curso práctico de formación','Antolín Muñoz Chaparro', 16, 1, 2017),
    ('9235152032659','Introducción a las bases de datos NoSQL usando MongoDB','Antonio Sarasa', 20, 1, 2016),
    ('9632054852156','Professional NoSQL','Shashank Tiwari', 20, 2, 2011);
    
-- Ejemplar
INSERT INTO ejemplar (idlibro) VALUES
	(1), (1), (1), (1),
    (2), (2), (2),
    (3), (3), (3),
    (4), (4),
	(5), (5),
    (6), (6),
    (7), (7), (7),
    (8), (8),
    (9),
    (10),
    (11),
    (12),
    (13);

SELECT * FROM v_libros_temas_idiomas WHERE titulo LIKE '%sql%'

    
