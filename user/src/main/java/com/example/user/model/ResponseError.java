package com.example.user.model;

import java.sql.Timestamp;
import java.util.List;

public class ResponseError {
    private List<ErrorDetails> error;
    
    public ResponseError(List<ErrorDetails> error) {
        this.error = error;
    }

    public List<ErrorDetails> getError() {
        return error;
    }

    public static class ErrorDetails {
        private Timestamp timestamp;
        private int codigo;
        private String detail;

        public ErrorDetails(Timestamp timestamp, int codigo, String detail) {
            this.timestamp = timestamp;
            this.codigo = codigo;
            this.detail = detail;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getDetail() {
            return detail;
        }
    }
}
