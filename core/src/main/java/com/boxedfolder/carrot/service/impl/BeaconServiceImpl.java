package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.service.BeaconService;
import org.springframework.stereotype.Service;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class BeaconServiceImpl extends CrudServiceImpl<Beacon, BeaconRepository> implements BeaconService {
    @Override
    public Beacon save(Beacon object) {
        // Check if there is already another uuid/major/minor combination
        Beacon oldObject = repository.findFirstByUuidAndMajorAndMinor(object.getUuid(),
                object.getMajor(),
                object.getMinor());

        if (oldObject != null && !object.equals(oldObject)) {
            throw new GeneralExceptions.AlreadyExistsException();
        }

        return super.save(object);
    }
}
