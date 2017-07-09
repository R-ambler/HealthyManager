
package app.controller.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "doGet", namespace = "http://controller.app/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doGet", namespace = "http://controller.app/", propOrder = {
    "arg0",
    "arg1"
})
public class DoGet {

    @XmlElement(name = "arg0", namespace = "")
    private javax.servlet.http.HttpServletRequest arg0;
    @XmlElement(name = "arg1", namespace = "")
    private javax.servlet.http.HttpServletResponse arg1;

    /**
     * 
     * @return
     *     returns HttpServletRequest
     */
    public javax.servlet.http.HttpServletRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(javax.servlet.http.HttpServletRequest arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns HttpServletResponse
     */
    public javax.servlet.http.HttpServletResponse getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(javax.servlet.http.HttpServletResponse arg1) {
        this.arg1 = arg1;
    }

}
