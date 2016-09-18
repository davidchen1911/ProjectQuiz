
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.Instruction;

/**
 * Servlet implementation class SecretCode
 */
@WebServlet("/SecretCode")
public class SecretCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String instruction = "We hope you'll send us your resume to our secret email address hashed below:\n"
			+ "856ef68d12e6c50f8b4547de50cd13098f38b2a65a74b7c88657df2a7d8c7deeeb9e2fb5ff3be3033d101240656d105880ac17c35f7a51b2948a89aecf25b717c8489c5f91e7ab0069e134d68723f9192498ade2cecb0eb9ac7048ec299dd1bba4d1194b81422e6edb62306995e8aad54f2036b0c68441fbd96873e36c84fbce706a886b308049fe9accb3ba4592ddc5\n"
			+ "First, we generated a series of string prefixes with lengths increasing by 2. For example, if our secret email address was helloworld@example.com, we would generate:\n"
			+ "he\n" + "hell\n" + "hellow\n" + "hellowor\n" + "...\n" + "helloworld@example.com\n\n"
			+ "Then, for every prefix s, we computed the following hash J:\n"
			+ "md5(s + md5(s))		[where + is the string concatenation operator].\n"
			+ "Finally, we concatenated all hash strings J to form the long hash above!\n\n"
			+ "For example, for helloworld@example.com,\n" + "we would compute:\n" + "md5('he' + md5('he')) +\n"
			+ "md5('hell' + md5('hell')) +\n" + "md5('hellow' + md5('hellow')) + ...";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SecretCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String useragent = request.getHeader("User-Agent");
		String referer = request.getHeader("Referer");
		response.setContentType("text/plain"); // what if I change it to txt
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		if (useragent != null && useragent.contains("Chrome") && useragent.contains("Android") && referer != null
				&& referer.contains("www.laioffer.com/project")) {
			out.print(instruction);
		} else {
			out.print("Your request is incorrect.");
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
		} catch (Exception e) { /* report an error */
		}
		response.setContentType("text/plain"); // what if I change it to txt
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try {
			JSONObject input = new JSONObject(jb.toString());
			if (input.has("name") && input.get("name").equals("alibaba") && input.has("passphrase")
					&& input.get("passphrase").equals("opensesame")) {
				out.print("Congratulations! You have found the secret code!\n\n" + "The secret code is:\n56854239\n");
			} else {
				out.print("Your request is incorrect.");
			}
		} catch (JSONException e) {
			out.print("Your request is incorrect.");
		}
		out.flush();
		out.close();
	}

}
