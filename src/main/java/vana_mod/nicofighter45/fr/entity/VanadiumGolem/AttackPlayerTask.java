package vana_mod.nicofighter45.fr.entity.VanadiumGolem;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

//public class AttackPlayerTask extends Task<VanadiumGolemEntity> {
//    public AttackPlayerTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
//        super(requiredMemoryState);
//    }
//
//    @Override
//    protected void run(ServerWorld world, VanadiumGolemEntity entity, long time) {
//        super.run(world, entity, time);
//        for(ServerPlayerEntity player : world.getPlayers()){
//            Path path = entity.getNavigation().findPathTo(player, 20);
//        }
//    }
//
//    @Override
//    protected boolean shouldRun(ServerWorld world, VanadiumGolemEntity entity) {
//        return super.shouldRun(world, entity);
//    }
//}