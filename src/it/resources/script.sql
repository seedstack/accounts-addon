--
-- Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
--
-- This file is part of SeedStack, An enterprise-oriented full development stack.
--
-- This Source Code Form is subject to the terms of the Mozilla Public
-- License, v. 2.0. If a copy of the MPL was not distributed with this
-- file, You can obtain one at http://mozilla.org/MPL/2.0/.
--

CREATE TABLE SEED_ACCOUNTS(ACCOUNT_ID varchar(225), HASHED_PASSWORD varchar(255), SALT varchar(255));
CREATE TABLE SEED_ROLES(ACCOUNT_ID varchar(255), ROLE varchar(255));
INSERT INTO SEED_ACCOUNTS VALUES('Obiwan', '5BCBD689FA503E51D3DC7A1D711EE8D851F6A70F46A83FCF', '2C80E0E3779909FA6335B25EC1D4470316630D210754317E');
INSERT INTO SEED_ROLES VALUES('Obiwan', 'SEED.JEDI');