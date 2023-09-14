package com.iamcaster.predict.service;

import org.springframework.stereotype.Service;

@Service
public class MapService {
	
	public String markerSVG(int rank, String regionName) {
		String marker = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"49\" height=\"55\" viewBox=\"0 0 49 55\" fill=\"none\">\r\n"
				+ "  <g filter=\"url(#filter0_d_224_9814)\">\r\n"
				+ "    <path d=\"M45 17.9785C45 31.4976 24.5 46.5 24.5 46.5C24.5 46.5 4 31.4976 4 17.9785C4 4.45941 13.1782 0 24.5 0C35.8218 0 45 4.45941 45 17.9785Z\" fill=\"url(#paint0_linear_224_9814)\" fill-opacity=\"0.44\" shape-rendering=\"crispEdges\"/>\r\n"
				+ "    <path d=\"M44.5 17.9785C44.5 21.2362 43.263 24.62 41.3646 27.8872C39.4685 31.1503 36.9333 34.2621 34.3861 36.9661C31.8404 39.6685 29.2929 41.9529 27.3811 43.5621C26.4256 44.3664 25.6298 45.0013 25.0736 45.4345C24.8369 45.6189 24.6436 45.7667 24.5 45.8755C24.3564 45.7667 24.1631 45.6189 23.9264 45.4345C23.3702 45.0013 22.5744 44.3664 21.6189 43.5621C19.7071 41.9529 17.1596 39.6685 14.6139 36.9661C12.0667 34.2621 9.53148 31.1503 7.63544 27.8872C5.73704 24.62 4.5 21.2362 4.5 17.9785C4.5 11.3438 6.74449 7.01098 10.3059 4.31844C13.8926 1.60674 18.8915 0.5 24.5 0.5C30.1085 0.5 35.1074 1.60674 38.6941 4.31844C42.2555 7.01098 44.5 11.3438 44.5 17.9785Z\" stroke=\"#BBBBBB\" shape-rendering=\"crispEdges\"/>\r\n"
				+ "  </g>\r\n"
				+ "  <defs>\r\n"
				+ "    <filter id=\"filter0_d_224_9814\" x=\"0\" y=\"0\" width=\"49\" height=\"54.5\" filterUnits=\"userSpaceOnUse\" color-interpolation-filters=\"sRGB\">\r\n"
				+ "      <feFlood flood-opacity=\"0\" result=\"BackgroundImageFix\"/>\r\n"
				+ "      <feColorMatrix in=\"SourceAlpha\" type=\"matrix\" values=\"0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0\" result=\"hardAlpha\"/>\r\n"
				+ "      <feOffset dy=\"4\"/>\r\n"
				+ "      <feGaussianBlur stdDeviation=\"2\"/>\r\n"
				+ "      <feComposite in2=\"hardAlpha\" operator=\"out\"/>\r\n"
				+ "      <feColorMatrix type=\"matrix\" values=\"0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0\"/>\r\n"
				+ "      <feBlend mode=\"normal\" in2=\"BackgroundImageFix\" result=\"effect1_dropShadow_224_9814\"/>\r\n"
				+ "      <feBlend mode=\"normal\" in=\"SourceGraphic\" in2=\"effect1_dropShadow_224_9814\" result=\"shape\"/>\r\n"
				+ "    </filter>\r\n"
				+ "    <linearGradient id=\"paint0_linear_224_9814\" x1=\"24.5\" y1=\"0\" x2=\"24.5\" y2=\"46.5\" gradientUnits=\"userSpaceOnUse\">\r\n"
				+ "      <stop offset=\"0.168076\" stop-color=\"#007AFF\"/>\r\n"
				+ "      <stop offset=\"0.461054\" stop-color=\"#50A4FF\" stop-opacity=\"0.685377\"/>\r\n"
				+ "      <stop offset=\"0.617309\" stop-color=\"#78B9FF\" stop-opacity=\"0.528065\"/>\r\n"
				+ "      <stop offset=\"0.819139\" stop-color=\"#A6D1FF\" stop-opacity=\"0.348388\"/>\r\n"
				+ "      <stop offset=\"1\" stop-color=\"white\" stop-opacity=\"0\"/>\r\n"
				+ "    </linearGradient>\r\n"
				+ "  </defs>\r\n"
				+ "<text x=\"25\" y=\"17\" font-size=\"12\" fill=\"black\" text-anchor=\"middle\" font-weight=\"bold\">"+regionName+"</text>'\\r\\n"
				+ "<text x=\"25\" y=\"30\" font-size=\"10\" fill=\"black\" text-anchor=\"middle\" font-weight=\"bold\">"+rank+"위</text>\\r\\n"
				+ "</svg>";
		
		return marker;
	}
	
	
}
