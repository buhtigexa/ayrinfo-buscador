package com.model;
import java.io.File;
public class CocoFileFilter extends CocoSimpleFilter{
			
	public CocoFileFilter(){
			super(null);
		}
		@Override
		public boolean      accept(Object obj) {
			return  ((!((File)obj).isDirectory() && !((File)obj).isHidden      ()) && ( ((File)obj).exists() && ((File)obj).canRead()) );
			
		}
	}

