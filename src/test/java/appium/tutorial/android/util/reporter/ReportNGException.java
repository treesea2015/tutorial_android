/*  1:   */package appium.tutorial.android.util.reporter;

/*  2:   */
/*  3:   */public class ReportNGException
/* 4: */extends RuntimeException
/* 5: */{
	/* 6: */public ReportNGException(String string)
	/* 7: */{
		/* 8:27 */super(string);
		/* 9: */}

	/* 10: */
	/* 11: */public ReportNGException(String string, Throwable throwable)
	/* 12: */{
		/* 13:33 */super(string, throwable);
		/* 14: */}
	/* 15: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: org.uncommons.reportng.ReportNGException
 * 
 * JD-Core Version: 0.7.0.1
 */