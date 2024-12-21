-- Enable pgcrypto for gen_random_uuid() if PostgreSQL 13+ (for UUID generation)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create the User table (includes both clients and agents for support)
CREATE TABLE "User" (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15),
    role VARCHAR(50) DEFAULT 'Client', -- Client, Agent
    birth_date DATE
);

-- Create the Message table (stores messages for support tickets)
CREATE TABLE Message (
    message_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES "User"(user_id),  -- Refers to the user who sent the message
    content TEXT NOT NULL,
    message_type VARCHAR(50) DEFAULT 'Text',  -- Text, Image, etc.
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Agency table (stores rental agency details)
CREATE TABLE Agency (
    agency_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL
);

-- Create the Payment table (tracks payments for rentals)
CREATE TABLE Payment (
    payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    rental_id UUID REFERENCES Rental(rental_id),
    amount NUMERIC(10, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) DEFAULT 'Paid'  -- Paid, Failed, Pending
);

-- Create the Rental table (manages vehicle reservations)
CREATE TABLE Rental (
    rental_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES "User"(user_id),  -- Refers to the user making the reservation
    vehicle_id UUID REFERENCES Vehicle(vehicle_id),
    pickup_agency_id UUID REFERENCES Agency(agency_id),
    dropoff_agency_id UUID REFERENCES Agency(agency_id),
    pickup_date TIMESTAMP NOT NULL,
    dropoff_date TIMESTAMP NOT NULL,
    rental_status VARCHAR(50) DEFAULT 'Confirmed', -- Confirmed, Cancelled, Completed
    total_amount NUMERIC(10, 2) NOT NULL
);

-- Create the Vehicle table (stores vehicle information)
CREATE TABLE Vehicle (
    vehicle_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    license_plate VARCHAR(50) UNIQUE NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    acriss_category VARCHAR(10) NOT NULL,  -- ACRISS category (e.g., Mini, SUV, Compact)
    agency_id UUID REFERENCES Agency(agency_id),
    vehicle_status VARCHAR(50) DEFAULT 'Available',  -- Available, Rented, Maintenance
    availability_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
