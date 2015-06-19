package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.service.AppService;
import com.boxedfolder.carrot.service.CrudService;
import org.springframework.stereotype.Service;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class AppServiceImpl extends CrudServiceImpl<App, AppRepository> implements AppService {
}
