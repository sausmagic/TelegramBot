package enumerations;



	public enum Group {

		SEXY("sexy"), SPORT("sport");

		private String GroupName;

		Group(String GroupName) {
			this.GroupName = GroupName;
		}

		public String getGroupName() {
			return GroupName;
		}
		
		public enum Category {

			MOTO("moto"), AUTO("auto"), CALCIO("calcio"), GIRL("girl");

			private Group group;

			private String CategoryName;

			Category(String CategoryName) {
				this.CategoryName = CategoryName;
			}

			public String getCategoryName() {
				return CategoryName;
			}

			Category(Group group) {
				this.group = group;
			}

			public boolean isInGroup(Group group) {
				return this.group == group;
			}
	}
}
