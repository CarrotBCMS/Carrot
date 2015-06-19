package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.service.AppService;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RequestMapping("/client/apps")
public class AppResource extends CrudResource<AppService, App> {
}
