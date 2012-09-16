package com.banane.z1guide;


public class Reaction {
	String textReaction;
	String imageReaction;
	String reactionType;
	String programId;
	String reactionId;
	
	public Reaction(String reactionId, String programId, String reactionType, String imageReaction, String textReaction){
		this.reactionId = reactionId;
		this.programId = programId;
		this.reactionType = reactionType;
		this.imageReaction = imageReaction;
		this.textReaction = textReaction;
	}
	
	public String getReactionId(){
		return this.reactionId;
	}
	public String getReactionType(){
		return this.reactionType;
	}
	public String getImageReaction() {
		return this.imageReaction;
	}
	
	public String getTextReaction(){
		return this.textReaction;
	}
	
	public String getProgramId(){
		return this.programId;
	}
}


