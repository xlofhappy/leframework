package org.le.cms.common.interfaces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Interface for generate the verify code
 *
 * @author xiaole
 * @date 2018-10-30 16:30:01
 */
public interface VerifyCodeGenerator {

    /**
     * generate the verify code
     *
     * @param w width
     * @param h height
     * @param l length
     *
     * @return {@link Code}
     */
    Code generate(int w, int h, int l) throws IOException;

    class Code {

        private String                code;
        private String                type;
        private ByteArrayOutputStream out;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ByteArrayOutputStream getOut() {
            return out;
        }

        public void setOut(ByteArrayOutputStream out) {
            this.out = out;
        }
    }

}
