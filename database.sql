DROP DATABASE IF EXISTS flypass;
CREATE DATABASE flypass;
USE flypass;

CREATE TABLE cliente
(
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion   ENUM ('CEDULA', 'PASAPORTE') NOT NULL,
    numero_identificacion VARCHAR(20)  NOT NULL UNIQUE,
    nombres               VARCHAR(50)  NOT NULL,
    apellidos             VARCHAR(50)  NOT NULL,
    correo_electronico    VARCHAR(100) NOT NULL,
    fecha_nacimiento      DATE         NOT NULL,
    enable                BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_creacion        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion    TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_nombre_length CHECK (LENGTH(nombres) >= 2),
    CONSTRAINT chk_apellido_length CHECK (LENGTH(apellidos) >= 2),
    CONSTRAINT chk_correo_valido CHECK (correo_electronico LIKE '%_@__%.__%')
);

CREATE TABLE cuenta
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    tipo_cuenta        ENUM ('CUENTA_CORRIENTE', 'CUENTA_AHORROS') NOT NULL,
    numero_cuenta      VARCHAR(10) NOT NULL UNIQUE,
    estado             ENUM ('ACTIVA', 'INACTIVA', 'CANCELADA')    NOT NULL DEFAULT 'ACTIVA',
    saldo              DECIMAL     NOT NULL,
    exenta_GMF         BOOLEAN     NOT NULL DEFAULT FALSE,
    fecha_creacion     TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    cliente_id         INT,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

CREATE TABLE transaccion
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    tipo_transaccion ENUM ('CONSIGNACION', 'RETIRO', 'TRANSFERENCIA') NOT NULL,
    monto            DECIMAL(15, 2) NOT NULL,
    fecha            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cuenta_origen    INT,
    cuenta_destino   INT,
    FOREIGN KEY (cuenta_origen) REFERENCES cuenta (id) ON DELETE CASCADE,
    FOREIGN KEY (cuenta_destino) REFERENCES cuenta (id) ON DELETE CASCADE,
    CHECK (cuenta_origen IS NOT NULL OR cuenta_destino IS NOT NULL)
);