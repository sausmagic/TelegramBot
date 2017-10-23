package enumerations;



	public enum Group {

		ALL("all",0), SEXY("sexy",1), SPORT("sport",2), INTERNAL_USERS("various",3);

		private String GroupName;
		private int groupValue;

		Group(String GroupName, int groupValue) {
			this.GroupName = GroupName;
			this.groupValue = groupValue;
		}

		public String getGroupName() {
			return GroupName;
		}
		
		public int getGroupValue() {
			return groupValue;
		}
		
		public enum Category {

			ALL("all",0), MOTO("moto",2.1), AUTO("auto", 2.2), CALCIO("calcio", 2.3), GIRL("girl", 1.1), INTERNAL_USERS("various",3);

			private Group group;
			

			private String CategoryName;
			private double CategoryValue;

			Category(String CategoryName, double CategoryValue) {
				this.CategoryName = CategoryName;
				this.CategoryValue= CategoryValue;
			}

			public String getCategoryName() {
				return CategoryName;
			}
			public double getCategoryValue() {
				return CategoryValue;
			}

			Category(Group group) {
				this.group = group;
			}

			public boolean isInGroup(Group group) {
				return this.group == group;
			}
	}
}
