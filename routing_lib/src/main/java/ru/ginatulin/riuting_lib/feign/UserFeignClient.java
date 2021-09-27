package ru.ginatulin.riuting_lib.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.ginatulin.riuting_lib.dto.UserDto;

@FeignClient("user-ms")
public interface UserFeignClient {
    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable String id);
}
