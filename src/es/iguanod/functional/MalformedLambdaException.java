package es.iguanod.functional;

/**
 * Thrown to indicate that a {@link es.iguanod.functional.Lambda Lambda} has
 * been called for {@link es.iguanod.functional.Lambda#eval evaluation}, but the
 * Lambda was created uncorrectly.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public class MalformedLambdaException extends Exception{

	private static final long serialVersionUID=3359729773716169466L;

	/**
	 * Constructs a new exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link java.lang.Throwable#initCause(java.lang.Throwable)
	 * Throwable.initCause(Throwable)}.
	 */
	public MalformedLambdaException(){
		super();
	}

	/**
	 * Constructs a new exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link java.lang.Throwable#initCause(java.lang.Throwable)
	 * Throwable.initCause(Throwable)}.
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by {@link java.lang.Throwable#getMessage()
	 * Throwable.getMessage()} method.
	 */
	public MalformedLambdaException(String message){
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this runtime exception's
	 * detail message.</p>
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by {@link java.lang.Throwable#getMessage()
	 * Throwable.getMessage()} method
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link java.lang.Throwable#getCause() Throwable.getCause()} method). A
	 * null value is permitted, and indicates that the cause is nonexistent or
	 * unknown.
	 */
	public MalformedLambdaException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of {@code (cause==null ? null : cause.toString())} (which
	 * typically contains the class and detail message of cause). This
	 * constructor is useful for runtime exceptions that are little more than
	 * wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link java.lang.Throwable#getCause() Throwable.getCause()} method). A
	 * null value is permitted, and indicates that the cause is nonexistent or
	 * unknown.
	 */
	public MalformedLambdaException(Throwable cause){
		super(cause);
	}
}
