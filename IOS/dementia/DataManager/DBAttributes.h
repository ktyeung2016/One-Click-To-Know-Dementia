//
//  DBAttributes.h
//  dementia
//
//

#ifndef dementia_DBAttributes_h
#define dementia_DBAttributes_h

/* Server */

#define KnowledgeTableAttributes @[@"id", @"title_zh", @"title_gb", @"html_content_zh", @"html_content_gb", @"redirect_session", @"create_user", @"create_date", @"update_user", @"update_date", @"display"]

#define DocumentTableAttributes @[@"id", @"category_id", @"organization_zh", @"organization_gb", @"service_name1_zh", @"service_name1_gb", @"service_name2_zh", @"service_name2_gb", @"service_name_output", @"district_zh", @"district_gb", @"first", @"second", @"third", @"four", @"family", @"helper", @"remark_zh", @"remark_gb", @"phone", @"website", @"temp", @"create_user", @"create_date", @"update_user", @"update_date", @"display", @"title_zh", @"title_gb"]

#define NewsTableAttributes @[@"id", @"title_zh", @"title_gb", @"html_content_zh", @"html_content_gb", @"create_user", @"create_date", @"update_user", @"update_date", @"display"]

/* Local */

#define ContactTableAttributes @[@"id", @"name", @"relationship", @"phone", @"email", @"remarks", @"file_media", @"create_user"]

#define GameScoreTableAttributes @[@"id", @"number_of_question", @"difficulty", @"score", @"user_id", @"date", @"game_type"]

#define GameLevelTableAttributes @[@"id", @"game_type", @"level", @"user_id"]

#define GameDemoTableAttributes @[@"id", @"game_type", @"shown", @"user_id"]

#define LocationDemoTableAttributes @[@"id", @"shown", @"user_id"]

#endif
