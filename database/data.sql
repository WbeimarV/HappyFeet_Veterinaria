USE veterinaria;

-- Catálogos 
INSERT INTO especie (nombre) VALUES
('Perro'), ('Gato'), ('Ave'), ('Reptil'), ('Roedor');

INSERT INTO producto_tipo (nombre) VALUES
('Medicamento'), ('Vacuna'), ('Insumo Médico'), ('Alimento'), ('Suplemento');

INSERT INTO evento_tipo (nombre) VALUES
('Vacunación'), ('Consulta'), ('Cirugía'), ('Desparasitación'), ('Emergencia');

INSERT INTO cita_estado (nombre) VALUES
('Programada'), ('En Proceso'), ('Finalizada'), ('Cancelada');

--  Razas 
INSERT INTO raza (especie_id, nombre)
VALUES
((SELECT id FROM especie WHERE nombre='Perro' LIMIT 1), 'Labrador'),
((SELECT id FROM especie WHERE nombre='Perro' LIMIT 1), 'Bulldog'),
((SELECT id FROM especie WHERE nombre='Perro' LIMIT 1), 'Pastor Alemán'),
((SELECT id FROM especie WHERE nombre='Perro' LIMIT 1), 'Chihuahua'),

((SELECT id FROM especie WHERE nombre='Gato' LIMIT 1), 'Siames'),
((SELECT id FROM especie WHERE nombre='Gato' LIMIT 1), 'Persa'),
((SELECT id FROM especie WHERE nombre='Gato' LIMIT 1), 'Bengala'),

((SELECT id FROM especie WHERE nombre='Ave' LIMIT 1), 'Canario'),
((SELECT id FROM especie WHERE nombre='Ave' LIMIT 1), 'Periquito'),

((SELECT id FROM especie WHERE nombre='Reptil' LIMIT 1), 'Iguana'),
((SELECT id FROM especie WHERE nombre='Roedor' LIMIT 1), 'Hamster'),
((SELECT id FROM especie WHERE nombre='Roedor' LIMIT 1), 'Conejo');

-- 3) Dueños
INSERT INTO dueno (nombre_completo, documento_identidad, direccion, telefono, email)
VALUES
('Ana Pérez', 'CC1001', 'Calle 10 #45-23', '3001230001', 'ana.perez@example.com'),
('Carlos Gómez', 'CC1002', 'Carrera 5 #12-45', '3001230002', 'carlos.gomez@example.com'),
('María López', 'CC1003', 'Av. Central 150', '3001230003', 'maria.lopez@example.com'),
('Juan Rodríguez', 'CC1004', 'Calle 22 #4-10', '3001230004', 'juan.rodriguez@example.com'),
('Luisa Martínez', 'CC1005', 'Cra 9 #33-50', '3001230005', 'luisa.martinez@example.com'),
('Diego Torres', 'CC1006', 'Cl. 7 #20-30', '3001230006', 'diego.torres@example.com'),
('Sofía Rivas', 'CC1007', 'Transversal 3 #11-02', '3001230007', 'sofia.rivas@example.com'),
('Pablo Herrera', 'CC1008', 'Zona Industrial 4', '3001230008', 'pablo.herrera@example.com');

-- 4) Mascotas 
INSERT INTO mascota (dueno_id, nombre, raza_id, fecha_nacimiento, sexo, url_foto)
VALUES
((SELECT id FROM dueno WHERE documento_identidad='CC1001' LIMIT 1),
 'Firulais',
 (SELECT id FROM raza WHERE nombre='Labrador' LIMIT 1),
 '2020-05-10', 'Macho', 'http://example.com/firulais.jpg'),

((SELECT id FROM dueno WHERE documento_identidad='CC1002' LIMIT 1),
 'Misu',
 (SELECT id FROM raza WHERE nombre='Siames' LIMIT 1),
 '2019-07-22', 'Hembra', 'http://example.com/misu.jpg'),

((SELECT id FROM dueno WHERE documento_identidad='CC1003' LIMIT 1),
 'Thor',
 (SELECT id FROM raza WHERE nombre='Bulldog' LIMIT 1),
 '2021-03-15', 'Macho', NULL),

((SELECT id FROM dueno WHERE documento_identidad='CC1004' LIMIT 1),
 'Luna',
 (SELECT id FROM raza WHERE nombre='Persa' LIMIT 1),
 '2018-11-30', 'Hembra', NULL),

((SELECT id FROM dueno WHERE documento_identidad='CC1005' LIMIT 1),
 'Nina',
 (SELECT id FROM raza WHERE nombre='Bengala' LIMIT 1),
 '2022-01-05', 'Hembra', NULL),

((SELECT id FROM dueno WHERE documento_identidad='CC1006' LIMIT 1),
 'Bolt',
 (SELECT id FROM raza WHERE nombre='Pastor Alemán' LIMIT 1),
 '2017-08-20', 'Macho', NULL),

((SELECT id FROM dueno WHERE documento_identidad='CC1007' LIMIT 1),
 'Piquito',
 (SELECT id FROM raza WHERE nombre='Periquito' LIMIT 1),
 '2023-02-12', 'Macho', NULL),

((SELECT id FROM dueno WHERE documento_identidad='CC1008' LIMIT 1),
 'Pelusa',
 (SELECT id FROM raza WHERE nombre='Hamster' LIMIT 1),
 '2024-04-01', 'Hembra', NULL);

-- 5) Historial médico
INSERT INTO historial_medico (mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado)
VALUES
((SELECT id FROM mascota WHERE nombre='Firulais' LIMIT 1), '2024-07-01 10:00:00', (SELECT id FROM evento_tipo WHERE nombre='Vacunación' LIMIT 1),
 'Aplicación de vacuna antirrábica (dosis completa).', 'Sin reacciones', 'Revisión en 7 días'),

((SELECT id FROM mascota WHERE nombre='Misu' LIMIT 1), '2024-09-10 09:30:00', (SELECT id FROM evento_tipo WHERE nombre='Consulta' LIMIT 1),
 'Chequeo por pérdida de apetito.', 'Gastritis leve', 'Dieta blanda y control'),

((SELECT id FROM mascota WHERE nombre='Thor' LIMIT 1), '2023-06-15 14:00:00', (SELECT id FROM evento_tipo WHERE nombre='Cirugía' LIMIT 1),
 'Esterilización', 'Post-operatorio normal', 'Control y analgésicos');

-- 6) Inventario 
INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, lote, cantidad_stock, stock_minimo, fecha_vencimiento, precio_venta)
VALUES
('Vacuna Rabia 1ml', (SELECT id FROM producto_tipo WHERE nombre='Vacuna' LIMIT 1), 'Vacuna antirrábica para uso subcutáneo', 'VetPharma', 'VR-2025-A', 30, 5, '2026-01-01', 45000.00),
('Antibiótico X 250mg', (SELECT id FROM producto_tipo WHERE nombre='Medicamento' LIMIT 1), 'Antibiótico de amplio espectro - suspensión', 'PetLab', 'ABX-250', 20, 3, '2025-06-15', 30000.00),
('Jeringa 5ml', (SELECT id FROM producto_tipo WHERE nombre='Insumo Médico' LIMIT 1), 'Jeringa desechable 5ml', 'MediTools', 'J5-2024', 200, 20, NULL, 1200.00),
('Pienso Adulto 5kg', (SELECT id FROM producto_tipo WHERE nombre='Alimento' LIMIT 1), 'Alimento balanceado para perros adultos', 'PetFoodCo', 'PF-5KG-2024', 50, 10, NULL, 60000.00),
('Suplemento Articular', (SELECT id FROM producto_tipo WHERE nombre='Suplemento' LIMIT 1), 'Suplemento para soporte articular', 'NutriPet', 'SA-01', 15, 2, '2025-12-31', 35000.00);

-- 7) Citas 
INSERT INTO cita (mascota_id, fecha_hora, motivo, estado_id)
VALUES
((SELECT id FROM mascota WHERE nombre='Firulais' LIMIT 1), '2025-10-01 09:00:00', 'Vacunación anual', (SELECT id FROM cita_estado WHERE nombre='Programada' LIMIT 1)),
((SELECT id FROM mascota WHERE nombre='Misu' LIMIT 1), '2025-10-02 10:30:00', 'Chequeo general', (SELECT id FROM cita_estado WHERE nombre='Programada' LIMIT 1)),
((SELECT id FROM mascota WHERE nombre='Luna' LIMIT 1), '2025-09-20 11:00:00', 'Problema dermatológico', (SELECT id FROM cita_estado WHERE nombre='Programada' LIMIT 1));

-- 8) Factura 
INSERT INTO factura (dueno_id, total)
VALUES ((SELECT id FROM dueno WHERE documento_identidad='CC1001' LIMIT 1), 95000.00);
SET @fact1 = LAST_INSERT_ID();

INSERT INTO item_factura (factura_id, producto_id, servicio_descripcion, cantidad, precio_unitario, subtotal)
VALUES
(@fact1, (SELECT id FROM inventario WHERE nombre_producto='Vacuna Rabia 1ml' LIMIT 1), NULL, 1, 45000.00, 45000.00),
(@fact1, (SELECT id FROM inventario WHERE nombre_producto='Jeringa 5ml' LIMIT 1), NULL, 3, 1200.00, 3600.00),
(@fact1, NULL, 'Consulta veterinaria', 1, 46400.00, 46400.00);

INSERT INTO factura (dueno_id, total)
VALUES ((SELECT id FROM dueno WHERE documento_identidad='CC1002' LIMIT 1), 30000.00);
SET @fact2 = LAST_INSERT_ID();

INSERT INTO item_factura (factura_id, producto_id, servicio_descripcion, cantidad, precio_unitario, subtotal)
VALUES
(@fact2, (SELECT id FROM inventario WHERE nombre_producto='Antibiótico X 250mg' LIMIT 1), NULL, 1, 30000.00, 30000.00);