USE db_example;

DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Films;
DROP TABLE IF EXISTS People;
DROP TABLE IF EXISTS Starships;

CREATE TABLE Orders (
    region TEXT NOT NULL,
    country TEXT NOT NULL,
    itemType TEXT NOT NULL,
    salesChannel TEXT NOT NULL,
    priority ENUM('C','H','M','L') CHARACTER SET binary,
    orderDate DATE NOT NULL,
    id INTEGER UNSIGNED NOT NULL UNIQUE,
    shipDate DATE NOT NULL,
    unitsSold INT UNSIGNED NOT NULL,
    unitPrice DECIMAL(10,2) UNSIGNED NOT NULL,
    unitCost DECIMAL(10,2) UNSIGNED NOT NULL,
    totalRevenue DECIMAL(10,2) UNSIGNED NOT NULL,
    totalCost DECIMAL(10,2) UNSIGNED NOT NULL,
    totalProfit DECIMAL(10,2) UNSIGNED NOT NULL
);

CREATE TABLE IF NOT EXISTS Films
(
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    episodeId TEXT NOT NULL,
    openingCrawl TEXT NOT NULL,
    director TEXT NOT NULL,
    producer TEXT NOT NULL,
    releaseDate TEXT NOT NULL,
    characters TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS People
(
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name` TEXT NOT NULL,
    height INT UNSIGNED NOT NULL,
    mass INT UNSIGNED NOT NULL,
    hairColor TEXT NOT NULL,
    skinColor TEXT NOT NULL,
    eyeColor TEXT NOT NULL,
    birthYear TEXT NOT NULL,
    gender TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Starships
(
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name` TEXT NOT NULL,
    model TEXT NOT NULL,
    manufacturer TEXT NOT NULL,
    cost_in_credits TEXT NOT NULL,
    length DECIMAL(10,1) UNSIGNED NOT NULL,
    max_atmosphering_speed TEXT NOT NULL,
    crew TEXT NOT NULL,
    passengers TEXT NOT NULL,
    cargo_capacity TEXT NOT NULL,
    consumables TEXT NOT NULL,
    hyperdrive_rating DECIMAL(10,1) UNSIGNED NOT NULL,
    MGLT TEXT NOT NULL,
    starship_class TEXT NOT NULL
);

CREATE TABLE Pilots (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    people_id INTEGER UNSIGNED NOT NULL,
    starship_id INTEGER UNSIGNED NOT NULL
);

CREATE TABLE Characters (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    people_id INTEGER UNSIGNED NOT NULL,
    film_id INTEGER UNSIGNED NOT NULL
);