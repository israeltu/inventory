package com.example.demo.exceptions;

import java.io.IOException;

public class ForbiddenOperationException extends IOException {

	
    public ForbiddenOperationException(String description) {
        super(description);
    }
}
