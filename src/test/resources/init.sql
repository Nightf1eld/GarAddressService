CREATE SCHEMA IF NOT EXISTS ADDRESS_SERVICE_PKG;

CREATE ALIAS IF NOT EXISTS ADDRESS_SERVICE_PKG.S_ADDR_IDX_INS FOR 'com.tkbbank.address_service.procedures.AddressServicePkg.idxAddressInsert';
CREATE ALIAS IF NOT EXISTS ADDRESS_SERVICE_PKG.S_ADDR_IDX_UPD FOR 'com.tkbbank.address_service.procedures.AddressServicePkg.idxAddressUpdate';