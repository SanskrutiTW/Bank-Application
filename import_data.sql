-- ==========================================
-- STEP 1: Create the reusable trigger function
-- (Always put this at the very top of the file)
-- ==========================================
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ language 'plpgsql';

-- ==========================================
-- STEP 2: Define your tables
-- ==========================================

-- Step A: Insert a mock Customer first (Assuming your customer table is named 'customer')

-- If your customer table has different columns, adjust them here!
INSERT INTO customer (id, first_name, last_name, email, created_at, updated_at)
VALUES (1, 'Alex', 'Mercer', 'alex.mercer@email.com');

-- Step B: Insert the matching Account
-- We explicitly pass values matching your Java validation rules (nullability, lengths, etc.)
INSERT INTO account (
    id, account_number, customer_id, account_type, account_uuid,
    balance, min_balance, status, ifsc_code, branch,
    created_at, updated_at, account_opened_at
) VALUES (
    1,
    'ACT1234567890',
    1,                          -- Links to the customer ID 1 above
    'SAVINGS',                  -- Assumes SAVINGS is an enum item in your AccountType
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', -- Generates a static UUID string for seeding
    1500.00,
    500.00,
    'ACTIVE',                   -- Matches your AccountStatus enum default
    'BARB0MYBANK',
    'Downtown Branch'
    );

-- ==========================================
-- STEP 3: Bind the triggers to the tables
-- (Always put these at the bottom, after the tables are created)
-- ==========================================

-- Trigger for the accounts table
CREATE TRIGGER update_account_timestamp
BEFORE UPDATE ON accounts
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();