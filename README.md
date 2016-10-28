# SeedStack accounts add-on 

[![Build status](https://travis-ci.org/seedstack/accounts-addon.svg?branch=master)](https://travis-ci.org/seedstack/accounts-addon) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.seedstack.addons.account/account/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/org.seedstack.addons.account/accounts)

This official add-on allows manage user accounts in local persistence. To CRUD accounts, use the AccountManagementService. It will automatically *hash* the account password with a salt different for each account. This addon uses seed-crypto to manage hashes.

##Configuration

Add the realm *AccountRealm* in your security properties. This addon exposes a jpa unit named *account-jpa-unit* that needs to be connected to a datasource.

# Copyright and license

This source code is copyrighted by [The SeedStack Authors](https://github.com/seedstack/seedstack/blob/master/AUTHORS) and
released under the terms of the [Mozilla Public License 2.0](https://www.mozilla.org/MPL/2.0/). 
