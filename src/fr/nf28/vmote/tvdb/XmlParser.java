package fr.nf28.vmote.tvdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public abstract class XmlParser {
	protected static final String ns = null;
	
	/** Public methods **/

	public List<Object> parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
	}

	/** Protected methods **/

	protected abstract List<Object> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException;
	
	
	protected int readInt(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		return Integer.valueOf(readString(parser, tagName));
	}
	
	protected long readLong(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		return Long.valueOf(readString(parser, tagName));
	}
	
	protected float readFloat(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		return Float.valueOf(readString(parser, tagName));
	}

	// For the tags, extracts their text values.
	protected String readString(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		String result = "";
		parser.require(XmlPullParser.START_TAG, ns, tagName);
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, ns, tagName);
		return result;
	}

	protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
