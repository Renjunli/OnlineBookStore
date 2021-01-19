package com.group7.store.service;

import com.group7.store.entity.user.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    int addAddress(Address address);

    int deleteAddress(int id);

    int modifyAddress(Address address);

    List<Address> addressList(String account);

    int setAddressOff(int id);
}
