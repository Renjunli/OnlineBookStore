package com.group7.store.service.impl;


import com.group7.store.entity.user.Address;
import com.group7.store.mapper.AddressMapper;
import com.group7.store.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("firstAddress")
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public int addAddress(Address address) {
        return addressMapper.addAddress(address);
    }

    @Override
    public int deleteAddress(int id) {
        return addressMapper.deleteAddress(id);
    }

    @Override
    public int modifyAddress(Address address) {
        return addressMapper.modifyAddress(address);
    }

    @Override
    public List<Address> addressList(String account) {
        return addressMapper.addressList(account);
    }

    @Override
    public int setAddressOff(int id) {
        return addressMapper.setAddressOff(id);
    }
}
