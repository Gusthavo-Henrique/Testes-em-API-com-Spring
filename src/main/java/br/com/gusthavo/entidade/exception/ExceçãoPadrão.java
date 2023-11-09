package br.com.gusthavo.entidade.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ExceçãoPadrão implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timeStamp;
	private String details;
	private String message;

	public ExceçãoPadrão() {

	}

	public ExceçãoPadrão(Date timeStamp, String details, String message) {
		super();
		this.timeStamp = timeStamp;
		this.details = details;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(details, message, timeStamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceçãoPadrão other = (ExceçãoPadrão) obj;
		return Objects.equals(details, other.details) && Objects.equals(message, other.message)
				&& Objects.equals(timeStamp, other.timeStamp);
	}

}
