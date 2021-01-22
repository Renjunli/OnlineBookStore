package com.group7.store.mapper;

import com.group7.store.entity.user.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Liuminge
 * @Date: 2021/1/19
 */
@Repository
public interface AddressMapper {
    int addAddress(Address address);

    int deleteAddress(int id);

    int modifyAddress(Address address);

    List<Address> addressList(String account);

    /**
     * 设置地址为删除状态
     *
     * @param id
     * @return
     */
    int setAddressOff(int id);
}