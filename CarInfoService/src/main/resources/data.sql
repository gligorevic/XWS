INSERT INTO BRAND (BRAND_NAME) VALUES ('BMW');
INSERT INTO BRAND (BRAND_NAME) VALUES ('Audi');
INSERT INTO BRAND (BRAND_NAME) VALUES ('VW');
INSERT INTO BRAND (BRAND_NAME) VALUES ('Peugeot');

INSERT INTO BODY_TYPE (BODY_TYPE_NAME) VALUES ('Coupe');
INSERT INTO BODY_TYPE (BODY_TYPE_NAME) VALUES ('SUV');
INSERT INTO BODY_TYPE (BODY_TYPE_NAME) VALUES ('Sedan');
INSERT INTO BODY_TYPE (BODY_TYPE_NAME) VALUES ('Hatchback');
INSERT INTO BODY_TYPE (BODY_TYPE_NAME) VALUES ('Jeep');

INSERT INTO FUEL_TYPE (FUEL_TYPE_NAME) VALUES ('Petrol');
INSERT INTO FUEL_TYPE (FUEL_TYPE_NAME) VALUES ('Diesel');
INSERT INTO FUEL_TYPE (FUEL_TYPE_NAME) VALUES ('Compressed natural gas (CNG)');
INSERT INTO FUEL_TYPE (FUEL_TYPE_NAME) VALUES ('Electric');
INSERT INTO FUEL_TYPE (FUEL_TYPE_NAME) VALUES ('Hybrid');

INSERT INTO GEAR_SHIFT_TYPE (GEAR_SHIFT_NAME) VALUES ('Manual');
INSERT INTO GEAR_SHIFT_TYPE (GEAR_SHIFT_NAME) VALUES ('Automatic');
INSERT INTO GEAR_SHIFT_TYPE (GEAR_SHIFT_NAME) VALUES ('Semi-Automatic');
INSERT INTO GEAR_SHIFT_TYPE (GEAR_SHIFT_NAME) VALUES ('One gear');

----------------------------------------------------------
-------------------!!! CAR MODELS !!!---------------------
----------------------------------------------------------
INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('M3', 1);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (1, 1);
INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (1, 3);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (1, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (1, 2);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (1, 5);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (1, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (1, 2);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (1, 3);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('X5', 1);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (2, 2);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (2, 3);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (2, 2);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (2, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (2, 2);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('A4', 2);
INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (3, 3);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (3, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (3, 2);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (3, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (3, 2);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('Q3', 2);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (4, 2);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (4, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (4, 5);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (4, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (4, 2);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('Golf', 3);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (5, 4);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (5, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (5, 2);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (5, 4);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (5, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (5, 2);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (5, 4);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('Polo', 3);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (6, 4);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (6, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (6, 2);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (6, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (6, 2);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('307', 4);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (7, 4);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (7, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (7, 2);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (7, 1);
----------------------------------------------------------

INSERT INTO MODEL (MODEL_NAME, BRAND_ID) VALUES ('508', 4);

INSERT INTO MODEL_BODY_TYPES (MODEL_ID, BODY_TYPE_ID) VALUES (8, 3);

INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (8, 1);
INSERT INTO MODEL_FUEL_TYPES (MODEL_ID, FUEL_TYPE_ID) VALUES (8, 2);

INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (8, 1);
INSERT INTO MODEL_GEAR_SHIFT_TYPES (MODEL_ID, GEAR_SHIFT_TYPE_ID) VALUES (8, 2);
----------------------------------------------------------






