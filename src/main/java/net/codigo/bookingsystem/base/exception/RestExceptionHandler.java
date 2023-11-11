package net.codigo.bookingsystem.base.exception;

import lombok.extern.slf4j.Slf4j;
import net.codigo.bookingsystem.base.CodigoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ApplicationErrorException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public CodigoResponse handleBizException(ApplicationErrorException e) {
		String msg = "app exception";
		if (e != null) {
			msg = e.getMsg();
			log.warn(e.toString());
		}
		return CodigoResponse.fail(msg);
	}


}
