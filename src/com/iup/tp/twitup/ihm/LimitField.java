package com.iup.tp.twitup.ihm;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitField extends PlainDocument 
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int max;
   public LimitField(int max) {
      super();
      this.max = max;
   }
   @Override
   public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
      if (text == null)
         return;
      if ((getLength() + text.length()) <= max) {
         super.insertString(offset, text, attr);
      }
   }
}