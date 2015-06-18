package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.service.AppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client/apps")
public class AppResource extends CrudResource<AppService, App> {
}
